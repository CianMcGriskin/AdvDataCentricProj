import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

interface Lecturer {
  lid: string;
  name: string;
  taxBand: string;
  salaryScale: number;
}

@Component({
  selector: 'app-update-lecturer',
  templateUrl: './update-lecturer.component.html',
  styleUrls: ['./update-lecturer.component.css']
})
export class UpdateLecturerComponent implements OnInit {
  selectedLecturer: Lecturer | null = null;
  lecturers: Lecturer[] = [];
  lidNum!: string | null;
  constructor(private route: ActivatedRoute, private http: HttpClient, private router: Router) {}

  ngOnInit() {
    this.http.get<Lecturer[]>('http://localhost:8080/lecturers').subscribe(
      data => {
        this.lecturers = data;
        this.route.paramMap.subscribe(params => {
          const lid = params.get('lid');
          this.lidNum = lid;
          if (lid) {
            const filteredLecturers = this.lecturers.filter(l => l.lid === lid);
            if (filteredLecturers.length > 0) 
              this.selectedLecturer = filteredLecturers[0];
            else {
              console.error(`Lecturer with lid ${lid} not found`);
            }
          }
        });
      },
      error => {
        console.error(error);
      }
    );
  }

  updateLecturer(lecturer: any) {
    const filteredLecturer = this.lecturers.filter(l => l.lid === lecturer.lid)[0];
    if (filteredLecturer) 
      this.selectedLecturer = {...filteredLecturer};
  }

  updateLecturerInfo() {
    if (this.selectedLecturer) {
      this.http.put(`http://localhost:8080/lecturer/${this.lidNum}`, this.selectedLecturer)
        .subscribe(
          () => {
            // Redirect to lecturer list page
            this.router.navigate(['/lecturers']);
          },
          error => {
            console.error('Error occurred while updating lecturer: ', error);
          }
        );
    }
  }
}
