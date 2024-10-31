import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskhistoryComponent } from './taskhistory.component';

describe('TaskhistoryComponent', () => {
  let component: TaskhistoryComponent;
  let fixture: ComponentFixture<TaskhistoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [TaskhistoryComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskhistoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });


  describe('getUsers', () => {
    it('should fetch users and update statusesSubject on success', () => {

      component.users = [{
        userId: 1,
        userName: 'Alice',
        email: 'mail',
        notifications: true
      }, {
        userId: 2,
        userName: 'Bob',
        email: 'mail',
        notifications: true
      }];

      expect(component.getUserName(1)).toEqual('Alice');
    });

  });
});
