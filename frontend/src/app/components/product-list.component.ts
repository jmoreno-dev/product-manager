import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ProductService } from '../services/product.service';
import { Product } from '../models/product.model';

@Component({
    selector: 'app-product-list',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './product-list.component.html',
    styleUrl: './product-list.component.scss'
})
export class ProductListComponent implements OnInit {
    products = signal<Product[]>([]);
    loading = signal(false);
    error = signal<string | null>(null);

    selectedProduct = signal<Product | null>(null);
    showForm = signal(false);

    formData = signal<Product>({
        name: '',
        description: '',
        price: 0,
        quantity: 0
    });

    constructor(private productService: ProductService) { }

    ngOnInit() {
        this.loadProducts();
    }

    loadProducts() {
        this.loading.set(true);
        this.error.set(null);
        this.productService.getProducts().subscribe({
            next: (data) => {
                this.products.set(data);
                this.loading.set(false);
            },
            error: (err) => {
                this.error.set('Error loading products');
                this.loading.set(false);
                console.error(err);
            }
        });
    }

    openForm(product?: Product) {
        if (product) {
            this.selectedProduct.set(product);
            this.formData.set({ ...product });
        } else {
            this.selectedProduct.set(null);
            this.formData.set({
                name: '',
                description: '',
                price: 0,
                quantity: 0
            });
        }
        this.showForm.set(true);
    }

    closeForm() {
        this.showForm.set(false);
        this.selectedProduct.set(null);
    }

    saveProduct() {
        const data = this.formData();

        if (!data.name || data.price < 0 || data.quantity < 0) {
            this.error.set('Please fill in all fields correctly');
            return;
        }

        this.loading.set(true);
        this.error.set(null);

        if (this.selectedProduct()?.id) {
            // Update
            this.productService.updateProduct(this.selectedProduct()!.id!, data).subscribe({
                next: () => {
                    this.loadProducts();
                    this.closeForm();
                    this.loading.set(false);
                },
                error: (err) => {
                    this.error.set('Error updating product');
                    this.loading.set(false);
                    console.error(err);
                }
            });
        } else {
            // Create
            this.productService.createProduct(data).subscribe({
                next: () => {
                    this.loadProducts();
                    this.closeForm();
                    this.loading.set(false);
                },
                error: (err) => {
                    this.error.set('Error creating product');
                    this.loading.set(false);
                    console.error(err);
                }
            });
        }
    }

    deleteProduct(id?: number) {
        if (!id) return;

        if (confirm('Are you sure you want to delete this product?')) {
            this.loading.set(true);
            this.error.set(null);
            this.productService.deleteProduct(id).subscribe({
                next: () => {
                    this.loadProducts();
                    this.loading.set(false);
                },
                error: (err) => {
                    this.error.set('Error deleting product');
                    this.loading.set(false);
                    console.error(err);
                }
            });
        }
    }

    updateFormData(field: keyof Product, value: any) {
        const current = this.formData();
        this.formData.set({
            ...current,
            [field]: field === 'price' || field === 'quantity' ? parseFloat(value) : value
        });
    }
}
