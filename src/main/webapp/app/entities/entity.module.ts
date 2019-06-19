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
      },
      {
        path: 'formation',
        loadChildren: './formation/formation.module#TothApplicationFormationModule'
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
