import { TestBed } from '@angular/core/testing';
import { TaskService } from './task.service';
import { ApiService } from './api.service';
import { ToastService } from './toast.service';
import { BehaviorSubject, of, throwError } from 'rxjs';
import { Task } from './task.service';

describe('TaskService', () => {
  let service: TaskService;
  let apiServiceMock: jest.Mocked<ApiService>;
  let toastServiceMock: jest.Mocked<ToastService>;

  beforeEach(() => {
    apiServiceMock = {
      createTask: jest.fn(),
      assignTaskToUser: jest.fn(),
      getTasks: jest.fn(),
      getPriorities: jest.fn(),
      getUsers: jest.fn(),
      getStatuses: jest.fn(),
    } as unknown as jest.Mocked<ApiService>;

    toastServiceMock = {
      addToast: jest.fn(),
    } as unknown as jest.Mocked<ToastService>;

    TestBed.configureTestingModule({
      providers: [
        TaskService,
        { provide: ApiService, useValue: apiServiceMock },
        { provide: ToastService, useValue: toastServiceMock },
      ],
    });

    service = TestBed.inject(TaskService);
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  describe('createTask', () => {

    it('should handle error when task creation fails', () => {
      apiServiceMock.createTask.mockReturnValue(throwError(() => new Error('Error')));

      service.createTask('Task 1', 'Description', 'HIGH', 'TODO', new Date(), 1, 1);

      expect(toastServiceMock.addToast).toHaveBeenCalledWith('Task not created !');
    });
  });

  describe('assignTaskToUser', () => {

    it('should handle error when assigning task to user fails', () => {
      apiServiceMock.assignTaskToUser.mockReturnValue(throwError(() => new Error('Error')));

      service.assignTaskToUser(1, 1, 1);

      expect(toastServiceMock.addToast).toHaveBeenCalledWith('Task not assigned !');
    });
  });

  describe('getTasks', () => {
    it('should fetch tasks and update tasksSubject on success', () => {
      const tasks: Task[] = [
        { id: 1, name: 'Task 1', description: 'Test task', priority: 'HIGH', status: 'TODO', dueDate: new Date() }
      ];
      apiServiceMock.getTasks.mockReturnValue(of(tasks));

      service.getTasks();

      expect(apiServiceMock.getTasks).toHaveBeenCalled();
      expect(service.tasksSubject.value).toEqual(tasks);
      expect(toastServiceMock.addToast).toHaveBeenCalledWith('Tasks fetched !');
    });

    it('should handle error when fetching tasks fails', () => {
      apiServiceMock.getTasks.mockReturnValue(throwError(() => new Error('Error')));

      service.getTasks();

      expect(toastServiceMock.addToast).toHaveBeenCalledWith('Tasks not fetched !');
    });
  });

  describe('getPriorities', () => {
    it('should handle error when fetching priorities fails', () => {
      apiServiceMock.getPriorities.mockReturnValue(throwError(() => new Error('Error')));

      service.getPriorities();

      expect(toastServiceMock.addToast).toHaveBeenCalledWith('Priorities not fetched !');
    });

    it('should fetch priorities and update prioritiesSubject on success', () => {
      const priorities = ['LOW', 'MEDIUM', 'HIGH'];
      apiServiceMock.getPriorities.mockReturnValue(of(priorities));

      service.getPriorities();

      expect(apiServiceMock.getPriorities).toHaveBeenCalled();
      expect(service.prioritiesSubject.value).toEqual(priorities);
      expect(toastServiceMock.addToast).toHaveBeenCalledWith('Priorities fetched !');
    });
  });

  describe('getStatuses', () => {
    it('should fetch statuses and update statusesSubject on success', () => {
      const statuses = ['TODO', 'IN_PROGRESS', 'DONE'];
      apiServiceMock.getStatuses.mockReturnValue(of(statuses));

      service.getStatuses();

      expect(apiServiceMock.getStatuses).toHaveBeenCalled();
      expect(service.statusesSubject.value).toEqual(statuses);
      expect(toastServiceMock.addToast).toHaveBeenCalledWith('Statuses fetched !');
    });

    it('should handle error when fetching statuses fails', () => {
      apiServiceMock.getStatuses.mockReturnValue(throwError(() => new Error('Error')));

      service.getStatuses();

      expect(toastServiceMock.addToast).toHaveBeenCalledWith('Statuses not fetched !');
    });
  });

});
