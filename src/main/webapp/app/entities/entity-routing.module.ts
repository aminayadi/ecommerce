import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'category',
        data: { pageTitle: 'Categories' },
        loadChildren: () => import('./categorydb/category/category.module').then(m => m.CategorydbCategoryModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'Products' },
        loadChildren: () => import('./productdb/product/product.module').then(m => m.ProductdbProductModule),
      },
      {
        path: 'client',
        data: { pageTitle: 'Clients' },
        loadChildren: () => import('./clientdb/client/client.module').then(m => m.ClientdbClientModule),
      },
      {
        path: 'message',
        data: { pageTitle: 'Messages' },
        loadChildren: () => import('./messagedb/message/message.module').then(m => m.MessagedbMessageModule),
      },
      {
        path: 'favoris',
        data: { pageTitle: 'Favorises' },
        loadChildren: () => import('./favorisdb/favoris/favoris.module').then(m => m.FavorisdbFavorisModule),
      },
      {
        path: 'request',
        data: { pageTitle: 'Requests' },
        loadChildren: () => import('./requestdb/request/request.module').then(m => m.RequestdbRequestModule),
      },
      {
        path: 'research',
        data: { pageTitle: 'Research' },
        loadChildren: () => import('./researchdb/research/research.module').then(m => m.ResearchdbResearchModule),
      },
      {
        path: 'fields',
        data: { pageTitle: 'Fields' },
        loadChildren: () => import('./categorydb/fields/fields.module').then(m => m.CategorydbFieldsModule),
      },
      {
        path: 'pfield',
        data: { pageTitle: 'Pfields' },
        loadChildren: () => import('./productdb/pfield/pfield.module').then(m => m.ProductdbPfieldModule),
      },
      {
        path: 'msg',
        data: { pageTitle: 'Msgs' },
        loadChildren: () => import('./chatmanagerdb/msg/msg.module').then(m => m.ChatmanagerdbMsgModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
