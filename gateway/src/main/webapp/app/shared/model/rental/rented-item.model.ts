import { type IRental } from '@/shared/model/rental/rental.model';

export interface IRentedItem {
  id?: number;
  bookId?: number | null;
  rentedDate?: Date | null;
  duDate?: Date | null;
  rental?: IRental | null;
}

export class RentedItem implements IRentedItem {
  constructor(
    public id?: number,
    public bookId?: number | null,
    public rentedDate?: Date | null,
    public duDate?: Date | null,
    public rental?: IRental | null,
  ) {}
}
