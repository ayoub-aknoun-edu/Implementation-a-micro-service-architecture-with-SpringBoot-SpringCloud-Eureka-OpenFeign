import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ActivatedRoute, Router} from "@angular/router";


@Component({
  selector: 'app-bills',
  templateUrl: './bills.component.html',
  styleUrls: ['./bills.component.css']
})
export class BillsComponent {
    bills : any;
    productsItems : any;
    custemerId !: number;
    constructor(private http:HttpClient, private router:Router, private route: ActivatedRoute) {
      this.custemerId = this.route.snapshot.params['customerId'];
    }

    ngOnInit(): void {
      this.http.get('http://localhost:8888/BILLING-SERVICE/bills/'+this.custemerId).subscribe({
        next: (data) => {
          this.bills = data;
          this.productsItems = this.bills.map((bill: any) => bill.productItems);
          let a = 0;


        },
        error: (err) => {
        }
    });
    }


  protected readonly console = console;
}
