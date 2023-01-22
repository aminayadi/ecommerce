import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';
import { Fields } from 'src/app/model/fields';
import { FieldsService } from 'src/app/services/fields.service';

declare var window: any;

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.css']
})
export class ListComponent implements OnInit {
 
  allFields: Fields[] = [];

  deleteModal: any;
  idTodelete: String = '';

 

  constructor(private fieldsService: FieldsService,
    private route: ActivatedRoute,
              private router:Router,) {}
 
  ngOnInit(): void {

    this.route.paramMap.subscribe((param : ParamMap) => {
      var id = String(param.get('id'));
      this.getByIdCategory(id);
    });


    

    this.deleteModal = new window.bootstrap.Modal(
      document.getElementById('deleteModal')
    );

  }
 
  getByIdCategory(id:string) {
    this.fieldsService.getallbyIdCategory(id).subscribe((data) => {
      this.allFields = data;
    });
  }

  openDeleteModal(id: string) {
    this.idTodelete = id;
    this.deleteModal.show();
  }

  delete() {
    this.fieldsService.delete(this.idTodelete).subscribe({
      next: (data) => {
        this.allFields = this.allFields.filter(_ => _.id != this.idTodelete)
        this.deleteModal.hide();
      },
    });
  }

 
  }

 
