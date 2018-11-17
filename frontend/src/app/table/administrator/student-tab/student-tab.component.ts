import {
  Component,
  OnInit,
  TemplateRef,
  OnDestroy,
  Input,
  Inject,
  forwardRef,
  EventEmitter,
  Output
} from '@angular/core';
import {BsModalRef, BsModalService} from "ngx-bootstrap";
import {Subscription} from "rxjs";
import {Ng4LoadingSpinnerService} from "ng4-loading-spinner";
import {DatePipe} from "@angular/common";
import {StudentAccount} from "../../../model/student-account";
import {Group} from "../../../model/group";
import {TableModelService} from "../../../service/table-model.service";
import {TableModel} from "../../../model/TableModel";
import {UserAccount} from "../../../model/UserAccount";
import {StudentProfessor} from "../../../model/StudentProfessor";


@Component({
  selector: 'student-tab',
  templateUrl: './student-tab.component.html',
  styleUrls: ['./student-tab.component.css']
})
export class StudentTabComponent implements OnInit, OnDestroy {

  @Input()
  public tableModel: TableModel;

  @Output()
  loadStudents: EventEmitter<any> = new EventEmitter<any>();

  /*info for pagination*/
  page: number = 1;
  totalNumberOfEntities: number;

  public searchButtonName: string = 'Search by';
  public searchText: string;

  /* needed for filterBy*/
  public studentField: string;
  public tempStudentForFilter: UserAccount;

  private modalRef: BsModalRef;

  public editMode: boolean = false;
  public editableStudent: UserAccount;

  private subscriptions: Subscription[] = [];

  public tempGroupId: number;

  constructor(private loadingService: Ng4LoadingSpinnerService,
              private tableModelService: TableModelService,
              private modalService: BsModalService,
              private datePipe: DatePipe) {
  }

  ngOnInit() {
    this.tempStudentForFilter = new UserAccount();
    this.tempStudentForFilter.studentProfessor = new StudentProfessor();
    this.tempStudentForFilter.studentProfessor.group = new Group();

    this.editableStudent = new UserAccount();
    this.editableStudent.studentProfessor = new StudentProfessor();
    this.editableStudent.studentProfessor.group = new Group();
    this.editableStudent.role = "student";
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach(subscription => subscription.unsubscribe());
  }

  openModal(template: TemplateRef<any>, studentAccount?: UserAccount): void {
    if (studentAccount) {
      this.editableStudent = UserAccount.cloneAccount(studentAccount);
      this.editMode = true;
      this.tempGroupId = this.editableStudent.studentProfessor.group.groupId;
    } else {
      this.refreshEditableStudent();
      this.editMode = false;
      this.tempGroupId = this.tableModel.groups.length != 0 ? this.tableModel.groups[0].groupId : 0;
    }
    this.modalRef = this.modalService.show(template);
  }

  public closeModal(): void {
    this.modalRef.hide();
  }

  public updateStudents(): void {
    this.loadStudents.emit();
    this.page = 1;
    this.totalNumberOfEntities = this.tableModel.students.length;
  }

  public refreshEditableStudent() {
    this.editableStudent = new UserAccount();
    this.editableStudent.studentProfessor = new StudentProfessor();
    this.editableStudent.studentProfessor.group = new Group();
    this.editableStudent.role = "student";
  }

  public addStudentAccount(): void {
    this.loadingService.show();

/*    if (this.editableStudent.studentProfessor.group !== null)*/
      this.editableStudent.studentProfessor.birthday =
        this.datePipe.transform(this.editableStudent.studentProfessor.birthday, 'yyyy-MM-dd');

    /*add group to student-tab according to chosen id*/
    for (let group of this.tableModel.groups){
      if (group.groupId == this.tempGroupId) {
        this.editableStudent.studentProfessor.group = group;
        break;
      }
    }

    this.subscriptions.push(this.tableModelService.saveStudent(this.editableStudent).subscribe(() => {
      this.updateStudents();
      this.closeModal();
      this.loadingService.hide();
      this.refreshEditableStudent();
    }));
  }

  public deleteStudentAccount(student: UserAccount): void {
    this.loadingService.show();
    this.subscriptions.push(this.tableModelService.deleteStudent(student).subscribe(() => {
      /*refresh all stored data in tableModel in case when we can delete parent node */
      this.updateStudents();
    }));
  }

  public searchTrigger(): void {
    if (this.searchButtonName === 'Search by')
      return;

    this.tempStudentForFilter = new UserAccount();
    this.tempStudentForFilter.studentProfessor = new StudentProfessor();
    this.tempStudentForFilter.studentProfessor.group = new Group();
    switch (this.studentField) {
      case 'firstName':
        this.tempStudentForFilter.studentProfessor.firstName = this.searchText;
        break;
      case 'lastName':
        this.tempStudentForFilter.studentProfessor.lastName = this.searchText;
        break;
      case 'birthday':
        this.tempStudentForFilter.studentProfessor.birthday = this.searchText;
        break;
      case 'groupId':
        if (this.searchText !== '')
          this.tempStudentForFilter.studentProfessor.group.groupId = Number(this.searchText);
        break;
    }
  }
}
