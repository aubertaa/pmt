import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { UserInputFormComponent } from './user-input-form.component';
import { AuthService } from '../../service/auth.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';

describe('UserInputFormComponent', () => {
  let component: UserInputFormComponent;
  let fixture: ComponentFixture<UserInputFormComponent>;
  let mockAuthService: jest.Mocked<AuthService>;
  let mockRouter: jest.Mocked<Router>;

  beforeEach(async () => {
    mockAuthService = {
      login: jest.fn(),
      register: jest.fn(),
    } as unknown as jest.Mocked<AuthService>;

    mockRouter = {
      navigate: jest.fn(),
    } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      declarations: [UserInputFormComponent],
      imports: [FormsModule], // Include FormsModule for template-driven forms
      providers: [
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UserInputFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should log isTryingToLogin on initialization', () => {
    console.log = jest.fn(); // Mock console.log
    component.isTryingToLogin = true;
    component.ngOnInit();
    expect(console.log).toHaveBeenCalledWith('isTryingToLogin: true');
  });

  it('should call AuthService login when form is valid and isTryingToLogin is true', () => {
    component.isTryingToLogin = true;

    const form = {
      form: { markAllAsTouched: jest.fn() },
      invalid: false,
      value: { username: 'testUser', password: 'testPass' },
      reset: jest.fn(),
    } as unknown as NgForm;

    component.onSubmit(form);
    expect(mockAuthService.login).toHaveBeenCalledWith('testUser', 'testPass');
    expect(form.reset).toHaveBeenCalled();
  });

  it('should call AuthService register when form is valid and isTryingToLogin is false', () => {
    component.isTryingToLogin = false;

    const form = {
      form: { markAllAsTouched: jest.fn() },
      invalid: false,
      value: {
        username: 'testUser',
        email: 'test@example.com',
        password: 'testPass',
      },
      reset: jest.fn(),
    } as unknown as NgForm;

    component.onSubmit(form);
    expect(mockAuthService.register).toHaveBeenCalledWith(
      'testUser',
      'test@example.com',
      'testPass'
    );
    expect(form.reset).toHaveBeenCalled();
  });

  it('should not call login or register if form is invalid', () => {
    const form = {
      form: { markAllAsTouched: jest.fn() },
      invalid: true,
      value: {},
      reset: jest.fn(),
    } as unknown as NgForm;

    component.onSubmit(form);
    expect(mockAuthService.login).not.toHaveBeenCalled();
    expect(mockAuthService.register).not.toHaveBeenCalled();
    expect(form.reset).not.toHaveBeenCalled();
  });

  it('should mark all form fields as touched when submitting', () => {
    const form = {
      form: { markAllAsTouched: jest.fn() },
      invalid: true,
      value: {},
      reset: jest.fn(),
    } as unknown as NgForm;

    component.onSubmit(form);
    expect(form.form.markAllAsTouched).toHaveBeenCalled();
  });
});
