import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MailService {
  private sendGridUrl = 'https://api.sendgrid.com/v3/mail/send';
  private apiKey = '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';

  constructor(private http: HttpClient) { }

  sendEmail (to: string, subject: string, content: string): Observable<any> {
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${this.apiKey}`,
      'Content-Type': 'application/json'
    });

    const emailData = {
      personalizations: [
        { to: [{ email: to }], subject: subject }
      ],
      from: { email: 'your-email@example.com' },
      content: [
        { type: 'text/plain', value: content }
      ]
    };

    return this.http.post(this.sendGridUrl, emailData, { headers });
  }
}
