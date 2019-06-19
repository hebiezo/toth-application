import { IFormation } from 'app/shared/model/formation.model';
import { IDocument } from 'app/shared/model/document.model';

export interface ICCP {
  id?: number;
  title?: string;
  formations?: IFormation[];
  documents?: IDocument[];
}

export class CCP implements ICCP {
  constructor(public id?: number, public title?: string, public formations?: IFormation[], public documents?: IDocument[]) {}
}
