import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProjectItemComponent } from './project-item.component';
import { ProjectService } from '../../service/project.service';
import { AuthService, User } from '../../service/auth.service';
import { Router } from '@angular/router';
import { of } from 'rxjs';
import { Project } from '../../service/project.service';

describe('ProjectItemComponent', () => {
  let component: ProjectItemComponent;
  let fixture: ComponentFixture<ProjectItemComponent>;
  let projectServiceMock: jest.Mocked<ProjectService>;
  let authServiceMock: jest.Mocked<AuthService>;
  let routerMock: jest.Mocked<Router>;

  beforeEach(async () => {
    projectServiceMock = {
      roles$: of(['Admin', 'Contributor']),
      getRoles: jest.fn(),
      changeRole: jest.fn(),
      addMember: jest.fn(),
      deleteProject: jest.fn(),
      getProjectsByUserId: jest.fn(),
      getProjectByProjectName: jest.fn(),
      getUserRole: jest.fn(),
      getProjectMembers: jest.fn(),
      saveProjectWithOwner: jest.fn(),
    } as unknown as jest.Mocked<ProjectService>;

    authServiceMock = {
      users$: of([{ userId: 1, userName: 'Test User' }] as User[]),
      getUsers: jest.fn(),
    } as unknown as jest.Mocked<AuthService>;

    routerMock = {
      navigate: jest.fn(),
      url: '/some-path',
    } as unknown as jest.Mocked<Router>;

    await TestBed.configureTestingModule({
      declarations: [ProjectItemComponent],
      providers: [
        { provide: ProjectService, useValue: projectServiceMock },
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock },
      ],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProjectItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize roles$ and users$ on ngOnInit', () => {
    component.ngOnInit();
    expect(projectServiceMock.getRoles).toHaveBeenCalled();
    expect(authServiceMock.getUsers).toHaveBeenCalled();
  });

  it('should set showMembersPopin to true on calling onSeeMembers', () => {
    component.onSeeMembers(1);
    expect(component.showMembersPopin).toBe(true);
  });

  it('should call changeRole with correct parameters', () => {
    const project = { id: 1 } as Project;
    const event = { target: { value: 'Contributor' } } as unknown as Event;

    component.onChangeRole(2, project, event);

    expect(projectServiceMock.changeRole).toHaveBeenCalledWith(2, project.id, 'Contributor');
  });

  it('should call addMember with correct parameters', () => {
    const user = { userId: 2 } as User;
    const project = { id: 3 } as Project;

    component.onAddMember(user, project);

    expect(projectServiceMock.addMember).toHaveBeenCalledWith(user.userId, project.id);
  });

  it('should return true if user is not a member of the project', () => {
    const user = { userId: 1 } as User;
    const project = { members: [{ id: { userId: 2 } }] } as Project;

    const result = component.isUserNotMember(project, user);

    expect(result).toBe(true);
  });

  it('should return false if user is a member of the project', () => {
    const user = { userId: 1 } as User;
    const project = { members: [{ id: { userId: 1 } }] } as Project;

    const result = component.isUserNotMember(project, user);

    expect(result).toBe(false);
  });

  it('should call deleteProject with correct parameter', () => {
    component.onDeleteProject(3);

    expect(projectServiceMock.deleteProject).toHaveBeenCalledWith(3);
  });

  it('should set showInvitePopin to true on calling onInviteUsers', () => {
    const event = new MouseEvent('click');
    component.onInviteUsers(event, 1);

    expect(component.showInvitePopin).toBe(true);
  });

  it('should set showInvitePopin to false on calling closeInvitePopin', () => {
    component.showInvitePopin = true;
    component.closeInvitePopin();

    expect(component.showInvitePopin).toBe(false);
  });

  it('should set showMembersPopin to false on calling closeMembersPopin', () => {
    component.showMembersPopin = true;
    component.closeMembersPopin();

    expect(component.showMembersPopin).toBe(false);
  });
});
