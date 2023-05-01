import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainPageComponent } from './main-page/main-page.component';
import { StudentsComponent } from './students/students.component';
import { LecturersComponent } from './lecturers/lecturers.component';
import { UpdateLecturerComponent } from './update-lecturer/update-lecturer.component';
import { FormsModule } from '@angular/forms';
import { UpdateStudentComponent } from './update-student/update-student.component';

@NgModule({
  declarations: [
    AppComponent,
    MainPageComponent,
    StudentsComponent,
    LecturersComponent,
    UpdateLecturerComponent,
    UpdateStudentComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
