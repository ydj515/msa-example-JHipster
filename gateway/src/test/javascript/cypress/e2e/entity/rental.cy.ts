import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('Rental e2e test', () => {
  const rentalPageUrl = '/rental';
  const rentalPageUrlPattern = new RegExp('/rental(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const rentalSample = {};

  let rental;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/rental/api/rentals+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/rental/api/rentals').as('postEntityRequest');
    cy.intercept('DELETE', '/services/rental/api/rentals/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (rental) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/rental/api/rentals/${rental.id}`,
      }).then(() => {
        rental = undefined;
      });
    }
  });

  it('Rentals menu should load Rentals page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('rental');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Rental').should('exist');
    cy.url().should('match', rentalPageUrlPattern);
  });

  describe('Rental page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(rentalPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Rental page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/rental/new$'));
        cy.getEntityCreateUpdateHeading('Rental');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/rental/api/rentals',
          body: rentalSample,
        }).then(({ body }) => {
          rental = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/rental/api/rentals+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/rental/api/rentals?page=0&size=20>; rel="last",<http://localhost/services/rental/api/rentals?page=0&size=20>; rel="first"',
              },
              body: [rental],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(rentalPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Rental page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('rental');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);
      });

      it('edit button click should load edit Rental page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Rental');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);
      });

      it('edit button click should load edit Rental page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Rental');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);
      });

      it('last delete button click should delete instance of Rental', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('rental').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentalPageUrlPattern);

        rental = undefined;
      });
    });
  });

  describe('new Rental page', () => {
    beforeEach(() => {
      cy.visit(`${rentalPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Rental');
    });

    it('should create an instance of Rental', () => {
      cy.get(`[data-cy="userId"]`).type('20439');
      cy.get(`[data-cy="userId"]`).should('have.value', '20439');

      cy.get(`[data-cy="retalStatus"]`).select('RENT_UNAVAILABLE');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        rental = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', rentalPageUrlPattern);
    });
  });
});
