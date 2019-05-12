import string
import random

def generate_email(size = 5):
    email = 'test{}@example.com'
    return email.format(''.join(random.choice(string.ascii_letters + string.digits) for _ in range(size)))

if __name__ == "__main__":
    print(generate_email())