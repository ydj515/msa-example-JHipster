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

describe('BookCatalog e2e test', () => {
  const bookCatalogPageUrl = '/book-catalog';
  const bookCatalogPageUrlPattern = new RegExp('/book-catalog(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bookCatalogSample = { title: '즐거운 승인 excepting', author: 'across' };

  let bookCatalog;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/bookcatalog/api/book-catalogs+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/bookcatalog/api/book-catalogs').as('postEntityRequest');
    cy.intercept('DELETE', '/services/bookcatalog/api/book-catalogs/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (bookCatalog) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/bookcatalog/api/book-catalogs/${bookCatalog.id}`,
      }).then(() => {
        bookCatalog = undefined;
      });
    }
  });

  it('BookCatalogs menu should load BookCatalogs page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('book-catalog');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('BookCatalog').should('exist');
    cy.url().should('match', bookCatalogPageUrlPattern);
  });

  describe('BookCatalog page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bookCatalogPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create BookCatalog page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/book-catalog/new$'));
        cy.getEntityCreateUpdateHeading('BookCatalog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookCatalogPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/bookcatalog/api/book-catalogs',
          body: bookCatalogSample,
        }).then(({ body }) => {
          bookCatalog = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/bookcatalog/api/book-catalogs+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/bookcatalog/api/book-catalogs?page=0&size=20>; rel="last",<http://localhost/services/bookcatalog/api/book-catalogs?page=0&size=20>; rel="first"',
              },
              body: [bookCatalog],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(bookCatalogPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details BookCatalog page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('bookCatalog');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookCatalogPageUrlPattern);
      });

      it('edit button click should load edit BookCatalog page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BookCatalog');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookCatalogPageUrlPattern);
      });

      it('edit button click should load edit BookCatalog page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('BookCatalog');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookCatalogPageUrlPattern);
      });

      it('last delete button click should delete instance of BookCatalog', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('bookCatalog').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookCatalogPageUrlPattern);

        bookCatalog = undefined;
      });
    });
  });

  describe('new BookCatalog page', () => {
    beforeEach(() => {
      cy.visit(`${bookCatalogPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('BookCatalog');
    });

    it('should create an instance of BookCatalog', () => {
      cy.get(`[data-cy="title"]`).type('hence 부끄러운 지원');
      cy.get(`[data-cy="title"]`).should('have.value', 'hence 부끄러운 지원');

      cy.get(`[data-cy="author"]`).type('hence 애프터셰이브');
      cy.get(`[data-cy="author"]`).should('have.value', 'hence 애프터셰이브');

      cy.get(`[data-cy="description"]`).type('inset 침략 underneath');
      cy.get(`[data-cy="description"]`).should('have.value', 'inset 침략 underneath');

      cy.get(`[data-cy="bookId"]`).type('31890');
      cy.get(`[data-cy="bookId"]`).should('have.value', '31890');

      cy.get(`[data-cy="rentCnt"]`).type('21457');
      cy.get(`[data-cy="rentCnt"]`).should('have.value', '21457');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        bookCatalog = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', bookCatalogPageUrlPattern);
    });
  });
});
