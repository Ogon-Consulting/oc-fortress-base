import { Routes } from '@angular/router';
import { TestCompComponent } from './test-module/test-comp/test-comp.component';
import { LoginCompComponent } from './login-module/login-comp/login-comp.component';
import { ShowhistoryCompComponent } from './test-module/showhistory-comp/showhistory-comp.component';
import { JoblogCompComponent } from './test-module/joblog-comp/joblog-comp.component';
import { TestdatamappingComponent } from './test-module/testdatamapping/testdatamapping.component';
import { SchedulehistoryComponent } from './test-module/schedulehistory/schedulehistory.component';
import { BundlelistComponent } from './test-module/bundlelist/bundlelist.component';
import { AdduserComponent } from './user/adduser/adduser.component';
import { UserlistComponent } from './user/userlist/userlist.component';
import { UsersearchComponent } from './user/usersearch/usersearch.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { ChartsviewComponent } from './charts/chartsview/chartsview.component';


export const routes: Routes = [
  {path:'login',component:LoginCompComponent},
  {path:'',component:LoginCompComponent},
  {path:'home',component:TestCompComponent},
  {path:'history',component:ShowhistoryCompComponent},
  {path:'joblog',component:JoblogCompComponent},
  {path:'testdata',component:TestdatamappingComponent},
  {path:'schedules',component:SchedulehistoryComponent},
  {path:'bundles',component:BundlelistComponent},
  {path:'adduser',component:AdduserComponent},
  {path:'users',component:UserlistComponent},
  {path:'usersearch',component:UsersearchComponent},
  {path:'edituser',component:EditUserComponent},
  {path:'charts',component:ChartsviewComponent}
];
