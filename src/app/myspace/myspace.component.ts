import { Component, ViewChild } from '@angular/core';
import { BreakpointObserver } from '@angular/cdk/layout';
import { MatSidenav } from '@angular/material/sidenav';
import { delay, filter } from 'rxjs/operators';
import { NavigationEnd, Router } from '@angular/router';
import { UntilDestroy, untilDestroyed } from '@ngneat/until-destroy';

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

  constructor(private observer: BreakpointObserver, private router: Router) {}

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
        break;
      case 'profile':
        this.isAnnounce= false ;
        this.isProfile= true ;
        this.isAbout= false ;
        break;  
      case 'about':
        this.isAnnounce= false ;
        this.isProfile= false ;
        this.isAbout= true ;
        break;              
    }
  }
}