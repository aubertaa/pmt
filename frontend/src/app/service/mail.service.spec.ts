import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { MailService } from './mail.service';

describe('MailService', () => {
  let service: MailService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MailService]
    });

    service = TestBed.inject(MailService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should send an email with correct headers and body', () => {
    const to = 'test@example.com';
    const subject = 'Test Subject';
    const content = 'Test Content';

    service.sendEmail(to, subject, content).subscribe(response => {
      expect(response).toBeTruthy();
    });

    const req = httpMock.expectOne(service['sendGridUrl']);
    expect(req.request.method).toBe('POST');
    expect(req.request.headers.get('Authorization')).toBe(`Bearer ${service['apiKey']}`);
    expect(req.request.headers.get('Content-Type')).toBe('application/json');

    const expectedBody = {
      personalizations: [
        { to: [{ email: to }], subject: subject }
      ],
      from: { email: 'your-email@example.com' },
      content: [
        { type: 'text/plain', value: content }
      ]
    };
    expect(req.request.body).toEqual(expectedBody);

    req.flush({ success: true }); // Simulate a successful response
  });

  it('should handle errors correctly', () => {
    const to = 'test@example.com';
    const subject = 'Test Subject';
    const content = 'Test Content';

    service.sendEmail(to, subject, content).subscribe(
      response => fail('Expected error, not a successful response'),
      error => {
        expect(error.status).toBe(500);
        expect(error.statusText).toBe('Internal Server Error');
      }
    );

    const req = httpMock.expectOne(service['sendGridUrl']);
    req.flush('Error', { status: 500, statusText: 'Internal Server Error' }); // Simulate error response
  });
});
