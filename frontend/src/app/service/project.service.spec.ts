import { TestBed } from '@angular/core/testing';
import { ProjectService, Project } from './project.service';
import { ApiService, CreatedResponse } from './api.service';
import { ToastService } from './toast.service';
import { of, throwError } from 'rxjs';

describe('ProjectService', () => {
  let projectService: ProjectService;
  let apiService: jest.Mocked<ApiService>;
  let toastService: jest.Mocked<ToastService>;

  beforeEach(() => {
    apiService = {
      addProject: jest.fn(),
      getProjects: jest.fn(),
      deleteProject: jest.fn(),
      getRoles: jest.fn(),
      changeRole: jest.fn(),
      addProjectMember: jest.fn()
    } as any;

    toastService = {
      addToast: jest.fn(),
    } as any;

    TestBed.configureTestingModule({
      providers: [
        ProjectService,
        { provide: ApiService, useValue: apiService },
        { provide: ToastService, useValue: toastService },
      ],
    });

    projectService = TestBed.inject(ProjectService);
  });

  describe('addProject', () => {
    it('should add a project successfully', () => {
      const projectName = 'Test Project';
      const description = 'Test Description';
      const startDate = new Date();
      const mockResponse = { id: 1 };

      apiService.addProject.mockReturnValue(of({ id: mockResponse.id } as CreatedResponse));
      apiService.getProjects.mockReturnValue(of({} as Project[]));

      projectService.addProject(projectName, description, startDate);

      projectService.projects$.subscribe((projects) => {
        expect(projects.length).toBe(1);
        expect(projects[0].projectName).toBe(projectName);
        expect(projects[0].description).toBe(description);
        expect(projects[0].startDate).toBe(startDate);
        expect(projects[0].id).toBe(mockResponse.id);
      });

      expect(toastService.addToast).toHaveBeenCalledWith(
        'Project ' + projectName + ' added !'
      );
    });

    it('should handle error when adding a project', () => {
      const projectName = 'Test Project';
      const description = 'Test Description';
      const startDate = new Date();

      apiService.addProject.mockReturnValue(
        throwError(() => new Error('Error'))
      );

      projectService.addProject(projectName, description, startDate);

      expect(toastService.addToast).toHaveBeenCalledWith(
        'Project ' + projectName + ' not created !'
      );
    });
  });

  describe('getProjects', () => {
    it('should fetch projects successfully', () => {
      const userId = 1;
      const mockProjects: Project[] = [
        {
          projectName: 'Project 1',
          description: 'Description 1',
          startDate: new Date(),
          id: 1,
        },
        {
          projectName: 'Project 2',
          description: 'Description 2',
          startDate: new Date(),
          id: 2,
        },
      ];

      apiService.getProjects.mockReturnValue(of(mockProjects));

      projectService.getProjects(userId);

      projectService.projects$.subscribe((projects) => {
        expect(projects.length).toBe(2);
      });

      expect(toastService.addToast).toHaveBeenCalledWith('Projects fetched !');
    });

    it('should handle error when fetching projects', () => {
      const userId = 1;

      apiService.getProjects.mockReturnValue(
        throwError(() => new Error('Error'))
      );

      projectService.getProjects(userId);

      expect(toastService.addToast).toHaveBeenCalledWith(
        'Projects not fetched !'
      );
    });
  });

  describe('deleteProject', () => {
    it('should delete a project successfully', () => {
      const projectName = 'Project to delete';
      const projectId = 1;
      const mockProject: Project = {
        projectName,
        description: 'Description',
        startDate: new Date(),
        id: projectId,
      };

      projectService.projectsSubject.next([mockProject]); // Initialize with a project

      const mockResponse = { id: 1 };
      apiService.deleteProject.mockReturnValue(
        of({ id: mockResponse.id } as CreatedResponse)
      );
      apiService.getProjects.mockReturnValue(of({} as Project[]));


      projectService.deleteProject(projectId);

      projectService.projects$.subscribe((projects) => {
        expect(projects.length).toBe(0); // Project should be deleted
      });

      expect(toastService.addToast).toHaveBeenCalledWith(
        'Project ' + projectName + ' deleted !'
      );
    });

    it('should handle error when deleting a project', () => {
      const projectId = 1;
      const mockProject: Project = {
        projectName: 'Project to delete',
        description: 'Description',
        startDate: new Date(),
        id: projectId,
      };

      projectService.projectsSubject.next([mockProject]); // Initialize with a project

      apiService.deleteProject.mockReturnValue(
        throwError(() => new Error('Error'))
      );

      projectService.deleteProject(projectId);

      expect(toastService.addToast).toHaveBeenCalledWith(
        'Project ' + mockProject.projectName + ' not deleted !'
      );
    });

    it('should handle project not found', () => {
      const projectId = 99; // Non-existing project ID

      projectService.projectsSubject.next([]); // Initialize with no projects

      projectService.deleteProject(projectId);

      expect(toastService.addToast).toHaveBeenCalledWith(`Project not found!`);
    });
  });

  describe('getRoles', () => {
    it('should fetch roles successfully', () => {
      const mockRoles = ['admin', 'user'];

      apiService.getRoles.mockReturnValue(of(mockRoles));

      projectService.getRoles();

      projectService.roles$.subscribe((roles) => {
        expect(roles.length).toBe(2);
        expect(roles[0]).toBe('admin');
        expect(roles[1]).toBe('user');
      });

      expect(toastService.addToast).toHaveBeenCalledWith('Roles fetched !');
    });

    it('should handle error when fetching roles', () => {
      apiService.getRoles.mockReturnValue(
        throwError(() => new Error('Error'))
      );

      projectService.getRoles();

      expect(toastService.addToast).toHaveBeenCalledWith('Roles not fetched !');
    });
  });

  describe('changeRole', () => {
    it('should change role successfully', () => {
      const userId = 1;
      const projectId = 1;
      const newRole = 'admin';

      apiService.changeRole.mockReturnValue(of({}));
      apiService.getProjects.mockReturnValue(of({} as Project[]));

      projectService.changeRole(userId, projectId, newRole);

      expect(toastService.addToast).toHaveBeenCalledWith('Role of ' + userId + ' changed !');
    });

    it('should handle error when changing role', () => {
      const userId = 1;
      const projectId = 1;
      const newRole = 'admin';

      apiService.changeRole.mockReturnValue(
        throwError(() => new Error('Error'))
      );
      apiService.getProjects.mockReturnValue(of({} as Project[]));

      projectService.changeRole(userId, projectId, newRole);

      expect(toastService.addToast).toHaveBeenCalledWith('Role of ' + userId + ' not changed !');
    });
  });

  describe('addMember', () => {
    it('should add member successfully', () => {
      const userId = 1;
      const projectId = 1;

      apiService.addProjectMember.mockReturnValue(of({}));
      apiService.getProjects.mockReturnValue(of({} as Project[]));

      projectService.addMember(userId, projectId);

      expect(toastService.addToast).toHaveBeenCalledWith('User ' + userId + ' added to project !');
    });

    it('should handle error when adding member', () => {
      const userId = 1;
      const projectId = 1;

      apiService.addProjectMember.mockReturnValue(
        throwError(() => new Error('Error'))
      );
      apiService.getProjects.mockReturnValue(of({} as Project[]));

      projectService.addMember(userId, projectId);

      expect(toastService.addToast).toHaveBeenCalledWith('User ' + userId + ' not added to project !');
    });
  });
});