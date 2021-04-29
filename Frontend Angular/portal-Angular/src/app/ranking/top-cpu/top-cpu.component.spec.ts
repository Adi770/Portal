import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopCPUComponent } from './top-cpu.component';

describe('TopCPUComponent', () => {
  let component: TopCPUComponent;
  let fixture: ComponentFixture<TopCPUComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopCPUComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopCPUComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
