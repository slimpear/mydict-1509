/* === INTROPROG ABGABE ===
 * Blatt 9, Aufgabe 2
 * Tutorium: t04
 * Gruppe: g05
 * Gruppenmitglieder:
 *  - Que Le Ba
 *  - Kerem Dede
 * ========================
 */

#include <stdio.h>
#include <stdlib.h>
#include <assert.h>
#include <string.h>
#include "heap.h"

/* Reserviere Speicher für einen Heap
 *
 * Der Heap soll dabei Platz für capacity elemente bieten.
 *
 * Um konsistent mit den Parametern für malloc zu sein, ist capacity
 * als size_t (entspricht unsigned long auf x86/64) deklariert.
 * Da es suberer ist, sind auch alle Indizes in dieser Aufgabe als
 * size_t deklariert. Ob man size_t oder int als Index für ein Array
 * verwendet, macht in der Praxis (auf x86/64) nur bei Arrays
 * mit mehr als 2.147.483.647 Elementen einen Unterschied.
 */
heap* heap_create(size_t capacity) {
 	heap* heap = malloc(1 * sizeof(heap));
	heap->elements = malloc(capacity * sizeof(int));
	heap->capacity = capacity;
	heap->size = 0;
	return heap;
}

/* Stellt die Heap Bedingung an index i wieder her
 *
 * Vorraussetzung: der linke und rechte Unterbaum von i
 * erfüllen die Heap-Bedingung bereits.
 */
void heapify(heap* h, size_t i) {
    size_t left = (2*i+1);
	size_t right = (2*i+2);
	size_t largest;
    //
	if (left < h->size && h->elements[left] > h->elements[i]) {
		largest = left;
	} else {
		largest = i;
	}
    //
	if (right < h->size && h->elements[right] > h->elements[largest]) {
		largest = right;
	}
    //
	if (largest != i) {
		size_t temp_index = h->elements[largest];
		h->elements[largest] = h->elements[i];
		h->elements[i] = temp_index;
        // rekursiv
		heapify(h, largest); 
	}
}

/* Holt einen Wert aus dem Heap
 *
 * Wenn der Heap nicht leer ist, wird die größte Zahl zurückgegeben.
 * Wenn der Heap leer ist, wird -1 zurückgegeben.
 */
int heap_extract_max(heap* h) {
    if (h->size == 0) {
		return -1;      // Heap empty 
	}
    // else...
	int max = h->elements[0];
	h->elements[0] = h->elements[(h->size)-1];
	(h->size)--;
	heapify(h, 0);
	return max;
}

/* Fügt einen Wert in den Heap ein
 *
 * Wenn der Heap bereits voll ist, wird -1 zurückgegeben,
 */
int heap_insert(heap* h, int key) {
    if (h->size == h->capacity) {
		return -1;
	}
    // 
	(h->size)++;
	int i = (h->size)-1;
	h->elements[i] = key;
    // interativ 
	while(i != 0 && h->elements[(i-1)/2] < h->elements[i]){
		int temp = h->elements[i];
		h->elements[i] = h->elements[(i-1)/2];
		h->elements[(i-1)/2] = temp;
		i = (i-1)/2;
	}
	return 0;
}

/* Gibt den Speicher von einem Heap wieder frei
 */
void heap_free(heap* h) {
	free(h->elements);
	free(h);	
}
