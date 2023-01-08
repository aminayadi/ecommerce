import {Component, Input, 
  OnChanges, SimpleChanges}     from '@angular/core';
import {AppService}             from '../service/app.service';
import {User}                   from '../data/user';
import {Message}                from '../data/message';

@Component({
  selector: 'users',
  templateUrl: '../template/users.component.html',
  styleUrls: ['../style/user.component.css']
})
export class UsersComponent implements OnChanges {

  @Input()
  inputMessage = ''

  users: User[] = new Array();

  constructor(private appService: AppService) {
    this.initUserList();
  }

  ngOnChanges(changes: SimpleChanges): void {
    console.log("changes :...................... ", changes);
    const chng = changes['inputMessage'];
    if(chng.currentValue!=""){
    let message: Message = JSON.parse(chng.currentValue);
    if(message)
    if (message.type == 'JOINED') {
      this.setUserStatus(message.from, true);
    } else if (message.type == 'LEFT') {
      this.setUserStatus(message.from, false);
    }
  }
  }

  initUserList() {
    this.appService.listUser().subscribe(response => {
      console.log("list of users .............",response);
      this.users = response;
      console.log("this.users .............",this.users);
    });
  }

  setUserStatus(userId: string, isOnline: boolean) {
    let user: User = this.users.find(u => u.id == userId);
    user.isOnline = isOnline;
  }

}