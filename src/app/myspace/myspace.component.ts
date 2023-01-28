import { Component, ViewChild } from '@angular/core';
import { BreakpointObserver } from '@angular/cdk/layout';
import { MatSidenav } from '@angular/material/sidenav';
import { delay, filter } from 'rxjs/operators';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';
import { TokenStorageService } from '../_services/token-storage.service';

@UntilDestroy()
@Component({
  selector: 'app-myspace',
  templateUrl: './myspace.component.html',
  styleUrls: ['./myspace.component.scss']
})
export class MyspaceComponent  {
  @ViewChild(MatSidenav)
  sidenav!: MatSidenav;

  isAnnounce: boolean = true ;
  isProfile: boolean = false ;
  isAbout: boolean = false ;
  isEdit: boolean = false ;
  id:String ='';

  constructor(private tokenStorage: TokenStorageService,
    private observer: BreakpointObserver, 
    private route: ActivatedRoute,
    private router: Router) {}

ngOnInit(): void {

this.route.paramMap.subscribe((param) => {
var path = String(param.get('path'));
 this.id = String(param.get('id'));
switch(path){
  case('newannounce'):
    this.isAnnounce= false ;
    this.isProfile=false ;
    this.isAbout=true ;
    this.isEdit= false ;
    break;
  case('editannounce'):
    this.isAnnounce= false ;
    this.isProfile=false ;
    this.isAbout=false ;
    this.isEdit= true ;
}

});
}




  ngAfterViewInit() {
    this.observer
      .observe(['(max-width: 800px)'])
      .pipe(delay(1), untilDestroyed(this))
      .subscribe((res) => {
        if (res.matches) {
          this.sidenav.mode = 'over';
          this.sidenav.close();
        } else {
          this.sidenav.mode = 'side';
          this.sidenav.open();
        }
      });

    this.router.events
      .pipe(
        untilDestroyed(this),
        filter((e) => e instanceof NavigationEnd)
      )
      .subscribe(() => {
        if (this.sidenav.mode === 'over') {
          this.sidenav.close();
        }
      });
  }

  goTo(path:string): void {
    console.log("path : ", path);
    switch(path){
      case 'announce':
        this.isAnnounce= true ;
        this.isProfile= false ;
        this.isAbout= false ;
        this.isEdit= false ;
        break;
      case 'profile':
        this.isAnnounce= false ;
        this.isProfile= true ;
        this.isAbout= false ;
        this.isEdit= false ;
        break;  
      case 'about':
        this.isAnnounce= false ;
        this.isProfile= false ;
        this.isAbout= true ;
        this.isEdit= false ;
        break;              
    }
  }
  logout(){
    console.log("logging out .............");
    localStorage.removeItem('auth-token');
    localStorage.removeItem('auth-user');
    localStorage.clear();
    this.router.navigate(['']);

  }
}