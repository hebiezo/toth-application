import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'document',
        loadChildren: './document/document.module#TothApplicationDocumentModule'
      },
      {
        path: 'ccp',
        loadChildren: './ccp/ccp.module#TothApplicationCCPModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TothApplicationEntityModule {}
