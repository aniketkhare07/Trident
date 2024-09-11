import { Status } from "./status";

export class BatchRequest {
    requestId!: number;
    username!: string;
    batchCode!: string;
    centerCode!: string;
    requestDate!: Date;
    status!: Status;
}