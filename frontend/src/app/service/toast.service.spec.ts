import { ToastService } from './toast.service';

describe('ToastService', () => {
  let service: ToastService;

  beforeEach(() => {
    service = new ToastService();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should initialize with an empty toasts array', () => {
    expect(service.toasts.length).toBe(0);
  });

  describe('addToast', () => {
    it('should add a toast message to the toasts array', () => {
      const message = 'Test Toast Message';
      service.addToast(message);

      expect(service.toasts.length).toBe(1);
      expect(service.toasts[0]).toBe(message);
    });
  });

  describe('remove', () => {
    it('should remove the toast at the specified index', () => {
      service.addToast('Toast 1');
      service.addToast('Toast 2');
      service.addToast('Toast 3');

      expect(service.toasts.length).toBe(3);

      service.remove(1); // Remove "Toast 2"

      expect(service.toasts.length).toBe(2);
      expect(service.toasts).toEqual(['Toast 1', 'Toast 3']); // "Toast 2" should be removed
    });

    it('should do nothing if the index is out of bounds', () => {
      service.addToast('Toast 1');
      service.addToast('Toast 2');

      const initialToasts = [...service.toasts];

      service.remove(10); // Out of bounds

      expect(service.toasts).toEqual(initialToasts); // No change
    });
  });
});
