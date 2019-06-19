import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDocument, Document } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';
import { ICCP } from 'app/shared/model/ccp.model';
import { CCPService } from 'app/entities/ccp';

@Component({
  selector: 'jhi-document-update',
  templateUrl: './document-update.component.html'
})
export class DocumentUpdateComponent implements OnInit {
  isSaving: boolean;

  ccps: ICCP[];

  editForm = this.fb.group({
    id: [],
    title: [],
    type: [],
    filename: [],
    cCPS: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected documentService: DocumentService,
    protected cCPService: CCPService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ document }) => {
      this.updateForm(document);
    });
    this.cCPService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ICCP[]>) => mayBeOk.ok),
        map((response: HttpResponse<ICCP[]>) => response.body)
      )
      .subscribe((res: ICCP[]) => (this.ccps = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(document: IDocument) {
    this.editForm.patchValue({
      id: document.id,
      title: document.title,
      type: document.type,
      filename: document.filename,
      cCPS: document.cCPS
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const document = this.createFromForm();
    if (document.id !== undefined) {
      this.subscribeToSaveResponse(this.documentService.update(document));
    } else {
      this.subscribeToSaveResponse(this.documentService.create(document));
    }
  }

  private createFromForm(): IDocument {
    const entity = {
      ...new Document(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      type: this.editForm.get(['type']).value,
      filename: this.editForm.get(['filename']).value,
      cCPS: this.editForm.get(['cCPS']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>) {
    result.subscribe((res: HttpResponse<IDocument>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackCCPById(index: number, item: ICCP) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
