import { ICCP } from 'app/shared/model/ccp.model';

export const enum TypeDocument {
  COURSE = 'COURSE',
  EXERCISE = 'EXERCISE',
  ABSENCE = 'ABSENCE'
}

export interface IDocument {
  id?: number;
  title?: string;
  type?: TypeDocument;
  filename?: string;
  cCPS?: ICCP[];
}

export class Document implements IDocument {
  constructor(public id?: number, public title?: string, public type?: TypeDocument, public filename?: string, public cCPS?: ICCP[]) {}
}
