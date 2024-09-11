import { Pipe, PipeTransform } from '@angular/core';
import { Status } from '../models/status';

@Pipe({
  name: 'status'
})
export class StatusPipe implements PipeTransform {
  transform(value: Status): string {
    switch (value) {
      case Status.Pending:
        return 'Pending';
      case Status.Approved:
        return 'Approved';
      case Status.Rejected:
        return 'Rejected';
      default:
        return 'Unknown';
    }
  }
}
