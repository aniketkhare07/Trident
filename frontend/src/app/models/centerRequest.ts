import { Status } from "./status";

export class CenterRequest {
    requestId!: number;
    username!: string;
    centerCode!: string;
    requestDate!: Date;
    status!: Status;
}