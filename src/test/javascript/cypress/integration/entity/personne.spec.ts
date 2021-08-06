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

describe('Personne e2e test', () => {
  let startingEntitiesCount = 0;

  before(() => {
    cy.window().then(win => {
      win.sessionStorage.clear();
    });

    cy.clearCookies();
    cy.intercept('GET', '/api/personnes*').as('entitiesRequest');
    cy.visit('');
    cy.login('admin', 'admin');
    cy.clickOnEntityMenuItem('personne');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.visit('/');
  });

  it('should load Personnes', () => {
    cy.intercept('GET', '/api/personnes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personne');
    cy.wait('@entitiesRequest');
    cy.getEntityHeading('Personne').should('exist');
    if (startingEntitiesCount === 0) {
      cy.get(entityTableSelector).should('not.exist');
    } else {
      cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
    }
    cy.visit('/');
  });

  it('should load details Personne page', () => {
    cy.intercept('GET', '/api/personnes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personne');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityDetailsButtonSelector).first().click({ force: true });
      cy.getEntityDetailsHeading('personne');
      cy.get(entityDetailsBackButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should load create Personne page', () => {
    cy.intercept('GET', '/api/personnes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personne');
    cy.wait('@entitiesRequest');
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Personne');
    cy.get(entityCreateSaveButtonSelector).should('exist');
    cy.visit('/');
  });

  it('should load edit Personne page', () => {
    cy.intercept('GET', '/api/personnes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personne');
    cy.wait('@entitiesRequest');
    if (startingEntitiesCount > 0) {
      cy.get(entityEditButtonSelector).first().click({ force: true });
      cy.getEntityCreateUpdateHeading('Personne');
      cy.get(entityCreateSaveButtonSelector).should('exist');
    }
    cy.visit('/');
  });

  it('should create an instance of Personne', () => {
    cy.intercept('GET', '/api/personnes*').as('entitiesRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personne');
    cy.wait('@entitiesRequest').then(({ request, response }) => (startingEntitiesCount = response.body.length));
    cy.get(entityCreateButtonSelector).click({ force: true });
    cy.getEntityCreateUpdateHeading('Personne');

    cy.get(`[data-cy="firstName"]`).type('Isabelle', { force: true }).invoke('val').should('match', new RegExp('Isabelle'));

    cy.get(`[data-cy="lastName"]`).type('Roussel', { force: true }).invoke('val').should('match', new RegExp('Roussel'));

    cy.get(`[data-cy="fatherName"]`).type('Handcrafted', { force: true }).invoke('val').should('match', new RegExp('Handcrafted'));

    cy.get(`[data-cy="sexe"]`).select('MASCULIN');

    cy.get(`[data-cy="motherFirstName"]`)
      .type('Kina Saint-Séverin Fish', { force: true })
      .invoke('val')
      .should('match', new RegExp('Kina Saint-Séverin Fish'));

    cy.get(`[data-cy="motherLastName"]`)
      .type('forecast d&#39;Alésia optical', { force: true })
      .invoke('val')
      .should('match', new RegExp('forecast d&#39;Alésia optical'));

    cy.get(`[data-cy="dateOfBirthday"]`).type('2021-06-10T10:13').invoke('val').should('equal', '2021-06-10T10:13');

    cy.get(`[data-cy="hourOfBithday"]`).type('2021-06-09T22:10').invoke('val').should('equal', '2021-06-09T22:10');

    cy.get(`[data-cy="numberRegister"]`).type('99883').should('have.value', '99883');

    cy.setFieldSelectToLastOfEntity('centre');

    cy.get(entityCreateSaveButtonSelector).click({ force: true });
    cy.scrollTo('top', { ensureScrollable: false });
    cy.get(entityCreateSaveButtonSelector).should('not.exist');
    cy.intercept('GET', '/api/personnes*').as('entitiesRequestAfterCreate');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personne');
    cy.wait('@entitiesRequestAfterCreate');
    cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount + 1);
    cy.visit('/');
  });

  it('should delete last instance of Personne', () => {
    cy.intercept('GET', '/api/personnes*').as('entitiesRequest');
    cy.intercept('DELETE', '/api/personnes/*').as('deleteEntityRequest');
    cy.visit('/');
    cy.clickOnEntityMenuItem('personne');
    cy.wait('@entitiesRequest').then(({ request, response }) => {
      startingEntitiesCount = response.body.length;
      if (startingEntitiesCount > 0) {
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount);
        cy.get(entityDeleteButtonSelector).last().click({ force: true });
        cy.getEntityDeleteDialogHeading('personne').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click({ force: true });
        cy.wait('@deleteEntityRequest');
        cy.intercept('GET', '/api/personnes*').as('entitiesRequestAfterDelete');
        cy.visit('/');
        cy.clickOnEntityMenuItem('personne');
        cy.wait('@entitiesRequestAfterDelete');
        cy.get(entityTableSelector).should('have.lengthOf', startingEntitiesCount - 1);
      }
      cy.visit('/');
    });
  });
});
