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

describe('RentedItem e2e test', () => {
  const rentedItemPageUrl = '/rented-item';
  const rentedItemPageUrlPattern = new RegExp('/rented-item(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const rentedItemSample = {};

  let rentedItem;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/rental/api/rented-items+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/rental/api/rented-items').as('postEntityRequest');
    cy.intercept('DELETE', '/services/rental/api/rented-items/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (rentedItem) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/rental/api/rented-items/${rentedItem.id}`,
      }).then(() => {
        rentedItem = undefined;
      });
    }
  });

  it('RentedItems menu should load RentedItems page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('rented-item');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('RentedItem').should('exist');
    cy.url().should('match', rentedItemPageUrlPattern);
  });

  describe('RentedItem page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(rentedItemPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create RentedItem page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/rented-item/new$'));
        cy.getEntityCreateUpdateHeading('RentedItem');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentedItemPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/rental/api/rented-items',
          body: rentedItemSample,
        }).then(({ body }) => {
          rentedItem = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/rental/api/rented-items+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/rental/api/rented-items?page=0&size=20>; rel="last",<http://localhost/services/rental/api/rented-items?page=0&size=20>; rel="first"',
              },
              body: [rentedItem],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(rentedItemPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details RentedItem page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('rentedItem');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentedItemPageUrlPattern);
      });

      it('edit button click should load edit RentedItem page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RentedItem');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentedItemPageUrlPattern);
      });

      it('edit button click should load edit RentedItem page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('RentedItem');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentedItemPageUrlPattern);
      });

      it('last delete button click should delete instance of RentedItem', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('rentedItem').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', rentedItemPageUrlPattern);

        rentedItem = undefined;
      });
    });
  });

  describe('new RentedItem page', () => {
    beforeEach(() => {
      cy.visit(`${rentedItemPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('RentedItem');
    });

    it('should create an instance of RentedItem', () => {
      cy.get(`[data-cy="bookId"]`).type('4752');
      cy.get(`[data-cy="bookId"]`).should('have.value', '4752');

      cy.get(`[data-cy="rentedDate"]`).type('2024-09-01');
      cy.get(`[data-cy="rentedDate"]`).blur();
      cy.get(`[data-cy="rentedDate"]`).should('have.value', '2024-09-01');

      cy.get(`[data-cy="duDate"]`).type('2024-08-31');
      cy.get(`[data-cy="duDate"]`).blur();
      cy.get(`[data-cy="duDate"]`).should('have.value', '2024-08-31');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        rentedItem = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', rentedItemPageUrlPattern);
    });
  });
});
