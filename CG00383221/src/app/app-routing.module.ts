import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LecturersComponent } from './lecturers/lecturers.component';
import { StudentsComponent } from './students/students.component';
import { MainPageComponent } from './main-page/main-page.component';
import { UpdateLecturerComponent } from './update-lecturer/update-lecturer.component';

const routes: Routes = [
  { path: '', component: MainPageComponent },
  { path: 'lecturers', component: LecturersComponent },
  { path: 'lecturers/edit/:lid', component: UpdateLecturerComponent },
  { path: 'students', component: StudentsComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
