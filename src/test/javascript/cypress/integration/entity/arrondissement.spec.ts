import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Arrondissement e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/arrondissements*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('arrondissement');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Arrondissements', () => {
    cy.intercept('GET', '/api/arrondissements*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('arrondissement');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Arrondissement').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Arrondissement page', () => {
    cy.intercept('GET', '/api/arrondissements*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('arrondissement');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('arrondissement');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Arrondissement page', () => {
    cy.intercept('GET', '/api/arrondissements*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('arrondissement');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Arrondissement');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Arrondissement page', () => {
    cy.intercept('GET', '/api/arrondissements*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('arrondissement');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Arrondissement');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Arrondissement', () => {
    cy.intercept('GET', '/api/arrondissements*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('arrondissement');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Arrondissement');

    cy.get(`[data-cy="arrondissementCode"]`).type('2679').should('have.value', '2679');

    cy.get(`[data-cy="arrondissementName"]`).type('c', { force: true }).invoke('val').should('match', new RegExp('c'));

    cy.setFieldSelectToLastOfEntity('commune');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/arrondissements*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('arrondissement');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Arrondissement', () => {
    cy.intercept('GET', '/api/arrondissements*').as('entitiesRequest');
    cy.intercept('DELETE', '/api/arrondissements/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('arrondissement');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('arrondissement').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/arrondissements*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('arrondissement');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
