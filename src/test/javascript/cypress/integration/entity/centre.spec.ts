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

describe('Centre e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/centres*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('centre');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Centres', () => {
    cy.intercept('GET', '/api/centres*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('centre');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Centre').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Centre page', () => {
    cy.intercept('GET', '/api/centres*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('centre');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('centre');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Centre page', () => {
    cy.intercept('GET', '/api/centres*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('centre');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Centre');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Centre page', () => {
    cy.intercept('GET', '/api/centres*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('centre');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Centre');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Centre', () => {
    cy.intercept('GET', '/api/centres*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('centre');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Centre');

    cy.get(`[data-cy="centreName"]`).type('c synergistic', { force: true }).invoke('val').should('match', new RegExp('c synergistic'));

    cy.get(`[data-cy="centreChief"]`)
      .type('Berkshire quantify overriding', { force: true })
      .invoke('val')
      .should('match', new RegExp('Berkshire quantify overriding'));

    cy.setFieldSelectToLastOfEntity('arrondissement');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/centres*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('centre');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Centre', () => {
    cy.intercept('GET', '/api/centres*').as('entitiesRequest');
    cy.intercept('DELETE', '/api/centres/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('centre');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('centre').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/centres*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('centre');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
