import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit {
  students: any[] = [];
  deleteError: string | undefined;
  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/students').subscribe(
      data => {
        this.students = data;
      },
      error => {
        console.error(error);
      }
    );
  }

  deleteStudent(sid: string) {
    this.http.delete<any>('http://localhost:8080/students/' + sid).subscribe(
      data => {
        // Remove deleted student from list
        this.students = this.students.filter(s => s.sid !== sid);
        // Reset error message
        this.deleteError = '';
      },
      error => {
        console.error(error);
        // Display error message
        this.deleteError = 'Student: ' + sid + ' cannot be deleted. He/she has associated modules.';
      }
    );
  }
  

}
