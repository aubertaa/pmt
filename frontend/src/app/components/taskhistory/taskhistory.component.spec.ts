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

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
