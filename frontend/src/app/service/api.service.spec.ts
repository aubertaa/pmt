import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import {
  ApiService,
  LoginResponse,
  CreatedResponse,
  ProjectRequest,
} from './api.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Project } from './project.service';

describe('ApiService', () => {
  let service: ApiService;
  let httpMock: HttpTestingController;
  const apiUrl = 'http://localhost:8081/api';

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ApiService],
    });

    service = TestBed.inject(ApiService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify(); // Ensure no outstanding HTTP requests
  });

  describe('registerUser', () => {
    it('should register a user successfully', () => {
      const mockResponse: CreatedResponse = { id: 1234 };
      const username = 'john';
      const email = 'john@example.com';
      const password = 'password';

      service.registerUser(username, email, password).subscribe((res) => {
        expect(res).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(`${apiUrl}/user`);
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual({
        userName: username,
        email: email,
        password: password,
      });

      req.flush(mockResponse); // Simulate successful response
    });

    it('should handle error during registration', () => {
      const username = 'john';
      const email = 'john@example.com';
      const password = 'password';
      const mockError = new HttpErrorResponse({
        status: 400,
        statusText: 'Bad Request',
        error: { error: 'Registration error' },
      });

      service.registerUser(username, email, password).subscribe({
        next: () => fail('Expected error'),
        error: (error) => {
          expect(error).toEqual(mockError);
        },
      });

      const req = httpMock.expectOne(`${apiUrl}/user`);
      expect(req.request.method).toBe('POST');
      req.flush(mockError.error, { status: 400, statusText: 'Bad Request' }); // Simulate error response
    });
  });

  describe('loginUser', () => {
    it('should log in a user successfully', () => {
      const username = 'john';
      const mockResponse: LoginResponse = {
        userName: 'john',
        email: 'john@example.com',
        password: 'password',
        userId: '0',
      };

      service.loginUser(username).subscribe((res) => {
        expect(res).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(`${apiUrl}/user?userName=${username}`);
      expect(req.request.method).toBe('GET');

      req.flush(mockResponse); // Simulate successful response
    });

    it('should handle error during login', () => {
      const username = 'john';
      const mockError = new HttpErrorResponse({
        status: 404,
        statusText: 'Not Found',
        error: { error: 'User not found' },
      });

      service.loginUser(username).subscribe({
        next: () => fail('Expected error'),
        error: (error) => {
          expect(error).toEqual(mockError);
        },
      });

      const req = httpMock.expectOne(`${apiUrl}/user?userName=${username}`);
      expect(req.request.method).toBe('GET');

      req.flush(mockError.error, { status: 404, statusText: 'Not Found' }); // Simulate error response
    });
  });

  describe('getProjects', () => {
    it('should retrieve projects by user ID', () => {
      const mockProjects: Project[] = [
        {
          projectName: 'Project 1',
          description: 'Test 1',
          startDate: new Date(),
          id: 1,
          userRole: 'admin',
        },
        {
          projectName: 'Project 2',
          description: 'Test 2',
          startDate: new Date(),
          id: 2,
          userRole: 'admin',
        },
      ];

      service.getProjects(1).subscribe((projects) => {
        expect(projects).toEqual(mockProjects);
      });

      const req = httpMock.expectOne(
        'http://localhost:8081/api/projects?userId=1'
      );
      expect(req.request.method).toBe('GET');
      req.flush(mockProjects);
    });
  });

  describe('deleteProject', () => {
    it('should delete a project by ID', () => {
      const mockResponse: CreatedResponse = { id: 1 };

      service.deleteProject(1).subscribe((response) => {
        expect(response).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(
        (request) =>
          request.url === 'http://localhost:8081/api/project' &&
          request.params.get('id') === '1'
      );
      expect(req.request.method).toBe('DELETE');
      req.flush(mockResponse);
    });
  });

  describe('addProject', () => {
    it('should add a new project', () => {
      const mockResponse: CreatedResponse = { id: 1 };
      const mockProjectRequest: ProjectRequest = {
        project: {
          projectName: 'New Project',
          description: 'Description',
          startDate: new Date(),
          id: 1,
          userRole: 'admin',
        },
        userId: 1,
      };

      localStorage.setItem('loggedInUserId', '1');
      service
        .addProject('New Project', 'Description', new Date())
        .subscribe((response) => {
          expect(response).toEqual(mockResponse);
        });

      const req = httpMock.expectOne('http://localhost:8081/api/project');
      expect(req.request.method).toBe('POST');
      req.flush(mockResponse);
    });
  });

  describe('getRoles', () => {
    it('should retrieve roles', () => {
      const mockRoles: string[] = ['admin', 'user'];

      service.getRoles().subscribe((roles) => {
        expect(roles).toEqual(mockRoles);
      });

      const req = httpMock.expectOne('http://localhost:8081/api/roles');
      expect(req.request.method).toBe('GET');
      req.flush(mockRoles);
    });
  });

  describe('addProjectMember', () => {
    it('should add a member to a project', () => {
      const mockResponse: CreatedResponse = { id: 1 };

      service.addProjectMember(1, 1).subscribe((response) => {
        expect(response).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(
        (request) =>
          request.url === 'http://localhost:8081/api/project/addMember' &&
          request.params.get('projectId') === '1' &&
          request.params.get('userId') === '1'
      );
      expect(req.request.method).toBe('POST');
      req.flush(mockResponse);
    });
  });

  describe('changeRole', () => {
    it('should change a user role', () => {
      const mockResponse: CreatedResponse = { id: 1 };

      service.changeRole(1, 1, 'admin').subscribe((response) => {
        expect(response).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(
        (request) =>
          request.url === 'http://localhost:8081/api/project/changeRole' &&
          request.params.get('projectId') === '1' &&
          request.params.get('userId') === '1' &&
          request.params.get('role') === 'admin'
      );
      expect(req.request.method).toBe('POST');
      req.flush(mockResponse);
    });
  });

  describe('catchError', () => {
    it('should handle error', () => {
      const mockError = new HttpErrorResponse({
        status: 404,
        statusText: 'Not Found',
        error: { error: 'Error' },
      });

      service.catchError(mockError).subscribe({
        next: () => fail('Expected error'),
        error: (error) => {
          expect(error).toEqual(mockError);
        },
      });
    });
  });

  describe('getPriorities', () => {
    it('should retrieve priorities', () => {
      const mockPriorities: string[] = ['LOW', 'MEDIUM', 'HIGH'];

      service.getPriorities().subscribe((priorities) => {
        expect(priorities).toEqual(mockPriorities);
      });

      const req = httpMock.expectOne('http://localhost:8081/api/priorities');
      expect(req.request.method).toBe('GET');
      req.flush(mockPriorities);
    });
  });


  describe('getStatuses', () => {
    it('should retrieve statuses', () => {
      const mockStatuses: string[] = ['TODO', 'IN_PROGRESS', 'DONE'];

      service.getStatuses().subscribe((statuses) => {
        expect(statuses).toEqual(mockStatuses);
      });

      const req = httpMock.expectOne('http://localhost:8081/api/statuses');
      expect(req.request.method).toBe('GET');
      req.flush(mockStatuses);
    });
  });

  describe('getUsers', () => {
    it('should retrieve users', () => {
      const mockUsers: string[] = ['Alice', 'Bob', 'Charlie'];

      service.getUsers().subscribe((users) => {
        expect(users).toBeDefined();
      });

      const req = httpMock.expectOne('http://localhost:8081/api/users');
      expect(req.request.method).toBe('GET');
      req.flush(mockUsers);
    });
  });

  describe('getTasks', () => {
    it('should retrieve tasks', () => {
      const mockTasks: string[] = ['Task 1', 'Task 2', 'Task 3'];

      service.getTasks().subscribe((tasks) => {
        expect(tasks).toBeDefined();
      });

      const req = httpMock.expectOne('http://localhost:8081/api/tasks');
      expect(req.request.method).toBe('GET');
      req.flush(mockTasks);
    });
  });


  describe('assignTaskToUser ', () => {
    it('should assign a task to a user', () => {
      const mockResponse: CreatedResponse = { id: 1 };

      service.assignTaskToUser(1, 1, 1).subscribe((response) => {
        expect(response).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(
        (request) =>
          request.url === 'http://localhost:8081/api/task/assign' &&
          request.params.get('userId') === '1' &&
          request.params.get('taskId') === '1'
      );
      expect(req.request.method).toBe('POST');
      req.flush(mockResponse);
    });
  });


  describe('createTask ', () => {
    it('should create a task', () => {
      const mockResponse: CreatedResponse = { id: 1 };
      const mockTaskRequest = {
        task: {
          id: 1,
          name: 'Task 1',
          description: 'Test task',
          priority: 'HIGH',
          status: 'TODO',
          dueDate: new Date(),
        },
        projectId: 1,
      };

      service
        .createTask(
          'Task 1',
          'Test task',
          'HIGH',
          'TODO',
          new Date(),
          1,
          0
        )
        .subscribe((response) => {
          expect(response).toEqual(mockResponse);
        });

      const req = httpMock.expectOne('http://localhost:8081/api/task');
      expect(req.request.method).toBe('POST');
      req.flush(mockResponse);
    });
  });


});
