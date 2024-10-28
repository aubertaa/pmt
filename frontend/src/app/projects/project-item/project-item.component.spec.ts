import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProjectItemComponent } from './project-item.component';
import { Project, ProjectService } from '../../service/project.service';
import { Task, TaskService } from '../../service/task.service';
import { AuthService, User } from '../../service/auth.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { NgForm } from '@angular/forms';

describe('ProjectItemComponent', () => {
  let component: ProjectItemComponent;
  let fixture: ComponentFixture<ProjectItemComponent>;
  let mockProjectService: jest.Mocked<ProjectService>;
  let mockTaskService: jest.Mocked<TaskService>;
  let mockAuthService: jest.Mocked<AuthService>;
  let mockRouter: jest.Mocked<Router>;

  beforeEach(async () => {
    mockProjectService = {
      changeRole: jest.fn(),
      addMember: jest.fn(),
      deleteProject: jest.fn(),
      hasChanges: jest.fn(),
    } as unknown as jest.Mocked<ProjectService>;

    mockTaskService = {
      createTask: jest.fn(),
      assignTaskToUser: jest.fn(),
      updateTask: jest.fn(),
      tasks$: of([]),
      getTasks: jest.fn(),
    } as unknown as jest.Mocked<TaskService>;

    mockAuthService = {} as jest.Mocked<AuthService>;

    mockRouter = {
      url: '/projects',
    } as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      declarations: [ProjectItemComponent],
      providers: [
        { provide: ProjectService, useValue: mockProjectService },
        { provide: TaskService, useValue: mockTaskService },
        { provide: AuthService, useValue: mockAuthService },
        { provide: Router, useValue: mockRouter },
      ],
      imports: [FormsModule],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should initialize with task observable', () => {
    expect(component.tasks$).toBe(mockTaskService.tasks$);
  });

  it('should get the correct username for a given user ID', () => {
    component.users = [{ userId: 1, userName: 'Alice' } as User];
    expect(component.getUserName(1)).toBe('Alice');
    expect(component.getUserName(2)).toBeUndefined();
  });

  it('should open and close task form', () => {
    component.onShowAddTaskForm();
    expect(component.showAddTaskForm).toBe(true);

    component.closeTaskForm();
    expect(component.showAddTaskForm).toBe(false);
  });

  it('should add a task when form is valid', () => {
    const mockForm = {
      value: {
        taskName: 'New Task',
        taskDescription: 'Task Description',
        taskPriority: 'HIGH',
        taskDueDate: new Date(),
      },
      form: {
        markAllAsTouched: jest.fn(),
        invalid: false,
      },
      reset: jest.fn(),
    } as unknown as NgForm;

    component.onAddTask(mockForm, 1);

    expect(mockTaskService.createTask).toHaveBeenCalledWith(
      'New Task',
      'Task Description',
      'HIGH',
      'TODO',
      mockForm.value.taskDueDate,
      1
    );
    expect(mockForm.reset).toHaveBeenCalled();
  });

  it('should add a member to the project', () => {
    const mockUser = { userId: 1 } as User;
    const mockProject = { id: 2 } as Project;

    component.onAddMember(mockUser, mockProject);

    expect(mockProjectService.addMember).toHaveBeenCalledWith(1, 2);
  });

  it('should change the role of a user in a project', () => {
    const mockProject = { id: 2 } as Project;
    const mockEvent = { target: { value: 'Manager' } } as unknown as Event;

    component.onChangeRole(1, mockProject, mockEvent);

    expect(mockProjectService.changeRole).toHaveBeenCalledWith(1, 2, 'Manager');
  });

  it('should assign a task to a user', () => {
    const mockUser = { userId: 1 } as User;
    component.toAssignTask = { id: 2 } as Task;

    component.onAssignTaskToUser(mockUser);

    expect(mockTaskService.assignTaskToUser).toHaveBeenCalledWith(1, 2);
  });

  it('should delete a project', () => {
    component.onDeleteProject(1);
    expect(mockProjectService.deleteProject).toHaveBeenCalledWith(1);
  });

  it('should toggle the visibility of tasks', () => {
    component.showTasks = false;
    component.onShowTasks();
    expect(component.showTasks).toBe(true);

    component.onShowTasks();
    expect(component.showTasks).toBe(false);
  });

  it('should show members popin', () => {
    component.onSeeMembers();
    expect(component.showMembersPopin).toBe(true);
  });

  it('should correctly determine if a user is not a project member', () => {
    const project = {
      members: [{ id: { userId: 2 } }],
    } as Project;

    const user = { userId: 1 } as User;

    expect(component.isUserNotMember(project, user)).toBe(true);
    expect(component.isUserNotMember(project, { userId: 2 } as User)).toBe(false);
  });

  it('onTaskChange should modify task.modified', () => {
    const task = { modified: false } as Task;

    component.onTaskChange(task);

    expect(task.modified).toBe(true);
  });

  it('updateTask should update a task', () => {
    const task = { id: 1 } as Task;
    const project = { id: 2 } as Project;

    component.updateTask(task, project);

    expect(mockTaskService.updateTask).toHaveBeenCalledWith(task, 2);
    expect(task.modified).toBe(false);
  });

  it('onShowTaskPopin  should show the task popin', () => {
    component.onShowTaskPopin({} as Task);
    expect(component.showTaskPopin).toBe(true);
  });

  it('closeTaskPopin should close the task popin', () => {
    component.closeTaskPopin();
    expect(component.showTaskPopin).toBe(false);
  });

  it('closeInvitePopin should close the invite popin', () => {
    component.closeInvitePopin();
    expect(component.showInvitePopin).toBe(false);
  });

  it('closeTasks should close the tasks', () => {
    component.closeTasks();
    expect(component.showTasks).toBe(false);
  });

  it('closeMembersPopin should close the members popin', () => {
    component.closeMembersPopin();
    expect(component.showMembersPopin).toBe(false);
  } );  


  

    



});
