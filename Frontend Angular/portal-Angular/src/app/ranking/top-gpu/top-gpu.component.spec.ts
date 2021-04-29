import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopGPUComponent } from './top-gpu.component';

describe('TopGPUComponent', () => {
  let component: TopGPUComponent;
  let fixture: ComponentFixture<TopGPUComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopGPUComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopGPUComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
