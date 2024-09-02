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

describe('Book e2e test', () => {
  const bookPageUrl = '/book';
  const bookPageUrlPattern = new RegExp('/book(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const bookSample = { title: '어댑터 마구', author: 'whereas oh 몹시' };

  let book;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/services/book/api/books+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/services/book/api/books').as('postEntityRequest');
    cy.intercept('DELETE', '/services/book/api/books/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (book) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/book/api/books/${book.id}`,
      }).then(() => {
        book = undefined;
      });
    }
  });

  it('Books menu should load Books page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('book');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Book').should('exist');
    cy.url().should('match', bookPageUrlPattern);
  });

  describe('Book page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(bookPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Book page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/book/new$'));
        cy.getEntityCreateUpdateHeading('Book');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/book/api/books',
          body: bookSample,
        }).then(({ body }) => {
          book = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/services/book/api/books+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/services/book/api/books?page=0&size=20>; rel="last",<http://localhost/services/book/api/books?page=0&size=20>; rel="first"',
              },
              body: [book],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(bookPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Book page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('book');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);
      });

      it('edit button click should load edit Book page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Book');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);
      });

      it.skip('edit button click should load edit Book page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Book');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);
      });

      it('last delete button click should delete instance of Book', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('book').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', bookPageUrlPattern);

        book = undefined;
      });
    });
  });

  describe('new Book page', () => {
    beforeEach(() => {
      cy.visit(`${bookPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Book');
    });

    it('should create an instance of Book', () => {
      cy.get(`[data-cy="title"]`).type('합금 expunge');
      cy.get(`[data-cy="title"]`).should('have.value', '합금 expunge');

      cy.get(`[data-cy="author"]`).type('sans 활동적인');
      cy.get(`[data-cy="author"]`).should('have.value', 'sans 활동적인');

      cy.get(`[data-cy="description"]`).type('boohoo 일찍');
      cy.get(`[data-cy="description"]`).should('have.value', 'boohoo 일찍');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        book = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', bookPageUrlPattern);
    });
  });
});
