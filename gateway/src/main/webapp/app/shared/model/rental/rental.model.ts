import { type RentalStatus } from '@/shared/model/enumerations/rental-status.model';
export interface IRental {
  id?: number;
  userId?: number | null;
  retalStatus?: keyof typeof RentalStatus | null;
}

export class Rental implements IRental {
  constructor(
    public id?: number,
    public userId?: number | null,
    public retalStatus?: keyof typeof RentalStatus | null,
  ) {}
}
