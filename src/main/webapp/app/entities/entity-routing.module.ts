import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'region',
        data: { pageTitle: 'Regions' },
        loadChildren: () => import('./region/region.module').then(m => m.RegionModule),
      },
      {
        path: 'department',
        data: { pageTitle: 'Departments' },
        loadChildren: () => import('./department/department.module').then(m => m.DepartmentModule),
      },
      {
        path: 'commune',
        data: { pageTitle: 'Communes' },
        loadChildren: () => import('./commune/commune.module').then(m => m.CommuneModule),
      },
      {
        path: 'arrondissement',
        data: { pageTitle: 'Arrondissements' },
        loadChildren: () => import('./arrondissement/arrondissement.module').then(m => m.ArrondissementModule),
      },
      {
        path: 'centre',
        data: { pageTitle: 'Centres' },
        loadChildren: () => import('./centre/centre.module').then(m => m.CentreModule),
      },
      {
        path: 'personne',
        data: { pageTitle: 'Personnes' },
        loadChildren: () => import('./personne/personne.module').then(m => m.PersonneModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
