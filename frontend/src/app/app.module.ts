import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatCheckboxModule } from '@angular/material/checkbox';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTableModule } from '@angular/material/table';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatListModule } from '@angular/material/list';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTabsModule } from '@angular/material/tabs';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { CarouselModule } from 'ngx-bootstrap/carousel';
import { AdminComponent } from './components/admin/admin.component';
import { HomeComponent } from './components/home/home.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { RequestComponent } from './components/user components/request/request.component';
import { DisplayPrintsComponent } from './components/user components/display-prints/display-prints.component';
import { AdminDashboardComponent } from './components/admin/admin components/admin-dashboard/admin-dashboard.component';
import { RegisterUserComponent } from './components/admin/admin components/register-user/register-user.component';
import { UserSettingsComponent } from './components/admin/admin components/user-settings/user-settings.component';
import { RequestApprovalComponent } from './components/admin/admin components/request-approval/request-approval.component';
import { StudentsComponent } from './components/admin/admin components/students/students.component';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { AuthInterceptor } from './guard/auth.interceptor';
import { UserAuthGuard } from './guard/user-auth.guard';
import { AdminAuthGuard } from './guard/admin-auth.guard';
import { StatusPipe } from './helper/status.pipe';
import { CenterTabComponent } from './components/admin/admin components/center-tab/center-tab.component';
import { BatchTabComponent } from './components/admin/admin components/batch-tab/batch-tab.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    AdminComponent,
    HomeComponent,
    NavbarComponent,
    RequestComponent,
    DisplayPrintsComponent,
    AdminDashboardComponent,
    RegisterUserComponent,
    UserSettingsComponent,
    RequestApprovalComponent,
    StudentsComponent,
    StatusPipe,
    CenterTabComponent,
    BatchTabComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    MatButtonModule,
    MatFormFieldModule,
    MatCardModule,
    MatCheckboxModule,
    MatInputModule,
    MatIconModule,
    MatSelectModule,
    MatTableModule,
    MatToolbarModule,
    MatPaginatorModule,
    MatTabsModule,
    MatSnackBarModule,
    CarouselModule.forRoot(),
    MatSidenavModule,
    MatListModule,
    HttpClientModule
  ],
  providers: [
    provideAnimationsAsync(),
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    UserAuthGuard,
    AdminAuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
