/* tslint:disable max-line-length */
import axios from 'axios';
import sinon from 'sinon';

import BookCatalogService from './book-catalog.service';
import { BookCatalog } from '@/shared/model/bookCatalog/book-catalog.model';

const error = {
  response: {
    status: null,
    data: {
      type: null,
    },
  },
};

const axiosStub = {
  get: sinon.stub(axios, 'get'),
  post: sinon.stub(axios, 'post'),
  put: sinon.stub(axios, 'put'),
  patch: sinon.stub(axios, 'patch'),
  delete: sinon.stub(axios, 'delete'),
};

describe('Service Tests', () => {
  describe('BookCatalog Service', () => {
    let service: BookCatalogService;
    let elemDefault;

    beforeEach(() => {
      service = new BookCatalogService();
      elemDefault = new BookCatalog('ABC', 'AAAAAAA', 'AAAAAAA', 'AAAAAAA', 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', async () => {
        const returnedFromService = { ...elemDefault };
        axiosStub.get.resolves({ data: returnedFromService });

        return service.find('ABC').then(res => {
          expect(res).toMatchObject(elemDefault);
        });
      });

      it('should not find an element', async () => {
        axiosStub.get.rejects(error);
        return service
          .find('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should create a BookCatalog', async () => {
        const returnedFromService = { id: 'ABC', ...elemDefault };
        const expected = { ...returnedFromService };

        axiosStub.post.resolves({ data: returnedFromService });
        return service.create({}).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not create a BookCatalog', async () => {
        axiosStub.post.rejects(error);

        return service
          .create({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should update a BookCatalog', async () => {
        const returnedFromService = { title: 'BBBBBB', author: 'BBBBBB', description: 'BBBBBB', bookId: 1, rentCnt: 1, ...elemDefault };

        const expected = { ...returnedFromService };
        axiosStub.put.resolves({ data: returnedFromService });

        return service.update(expected).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not update a BookCatalog', async () => {
        axiosStub.put.rejects(error);

        return service
          .update({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should partial update a BookCatalog', async () => {
        const patchObject = { description: 'BBBBBB', ...new BookCatalog() };
        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = { ...returnedFromService };
        axiosStub.patch.resolves({ data: returnedFromService });

        return service.partialUpdate(patchObject).then(res => {
          expect(res).toMatchObject(expected);
        });
      });

      it('should not partial update a BookCatalog', async () => {
        axiosStub.patch.rejects(error);

        return service
          .partialUpdate({})
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should return a list of BookCatalog', async () => {
        const returnedFromService = { title: 'BBBBBB', author: 'BBBBBB', description: 'BBBBBB', bookId: 1, rentCnt: 1, ...elemDefault };
        const expected = { ...returnedFromService };
        axiosStub.get.resolves([returnedFromService]);
        return service.retrieve({ sort: {}, page: 0, size: 10 }).then(res => {
          expect(res).toContainEqual(expected);
        });
      });

      it('should not return a list of BookCatalog', async () => {
        axiosStub.get.rejects(error);

        return service
          .retrieve()
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });

      it('should delete a BookCatalog', async () => {
        axiosStub.delete.resolves({ ok: true });
        return service.delete('ABC').then(res => {
          expect(res.ok).toBeTruthy();
        });
      });

      it('should not delete a BookCatalog', async () => {
        axiosStub.delete.rejects(error);

        return service
          .delete('ABC')
          .then()
          .catch(err => {
            expect(err).toMatchObject(error);
          });
      });
    });
  });
});
