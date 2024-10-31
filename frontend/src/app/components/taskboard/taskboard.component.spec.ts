import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskboardComponent } from './taskboard.component';
import { ApiService } from '../../service/api.service';

describe('TaskboardComponent', () => {
  let component: TaskboardComponent;
  let fixture: ComponentFixture<TaskboardComponent>;
  let apiServiceMock: jest.Mocked<ApiService>;


  beforeEach(async () => {

    apiServiceMock = {
      createTask: jest.fn(),
      assignTaskToUser: jest.fn(),
      getTasks: jest.fn(),
      getPriorities: jest.fn(),
      getUsers: jest.fn(),
      getStatuses: jest.fn(),
    } as unknown as jest.Mocked<ApiService>;


    await TestBed.configureTestingModule({
      declarations: [TaskboardComponent]
    })
      .compileComponents();

    fixture = TestBed.createComponent(TaskboardComponent);
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
