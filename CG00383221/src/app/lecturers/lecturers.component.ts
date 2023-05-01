import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-lecturers',
  templateUrl: './lecturers.component.html',
  styleUrls: ['./lecturers.component.css']
})
export class LecturersComponent implements OnInit {
  lecturers: any[] = [];

  constructor(private http: HttpClient, private router: Router) { }

  ngOnInit(): void {
    this.http.get<any[]>('http://localhost:8080/lecturers').subscribe(
      data => {
        this.lecturers = data;
      },
      error => {
        console.error(error);
      }
    );
  }

  updateLecturer(lecturer: any) {
    // Redirect to edit page with lecturer id in URL
    this.router.navigate(['/lecturers/edit', lecturer.lid]);
  }
}
