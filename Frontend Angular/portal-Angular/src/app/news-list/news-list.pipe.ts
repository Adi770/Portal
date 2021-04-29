
import { Pipe, PipeTransform } from '@angular/core';


@Pipe({ name: 'striphtml' })
export class NewsListPipe implements PipeTransform {
    transform(value: string): any {
        return value.replace(/<.*?>/g, ''); // replace tags
    }
}