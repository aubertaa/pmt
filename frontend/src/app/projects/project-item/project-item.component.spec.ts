import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ProjectItemComponent } from './project-item.component';
import { ProjectService } from '../../service/project.service';
import { Router } from '@angular/router';

// Create a mock ProjectService
class MockProjectService {
  deleteProject = jest.fn();
}

// Create a mock Router
class MockRouter {
  url = '/mock-url'; // Set a mock URL for testing
}

describe('ProjectItemComponent', () => {
  let component: ProjectItemComponent;
  let fixture: ComponentFixture<ProjectItemComponent>;
  let projectService: MockProjectService;

  beforeEach(() => {
    projectService = new MockProjectService();

    TestBed.configureTestingModule({
      declarations: [ProjectItemComponent],
      providers: [
        { provide: ProjectService, useValue: projectService },
        { provide: Router, useValue: new MockRouter() },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(ProjectItemComponent);
    component = fixture.componentInstance;
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should call deleteProject on ProjectService when onDeleteProject is called', () => {
    const projectId = 1;
    component.onDeleteProject(projectId);

    expect(projectService.deleteProject).toHaveBeenCalledWith(projectId);
  });

});
