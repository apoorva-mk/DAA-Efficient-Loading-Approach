class Box:
    def __init__(self,l,w,h):
        self.x = -1
        self.y = -1
        self.z = -1
        self.l = l
        self.w = w
        self.h = h
    
    def set_bounds(self,x,y,z):
        self.x = x
        self.y = y
        self.z = z
    
    def is_longer_than(self,B):
        return self.l >= B.l
    
    def is_wider_than(self,B):
        return self.w >= B.w

class Container:
    def __init__(self,l,w):
        self.a_x = 0
        self.a_y = 0
        self.b_y = 0
        self.c_y = 0
        
        self.l = l
        self.w = w

    def fill_a(self,B):
        self.a_x += B.l

    def fill_b(self,B):
        pass
    
    def fill_c(self,B):
        self.c_x += B.l
    

def main():
    box1 = Box(10,10,10)    
    box2 = Box(10,20,10)
    container = Container(20,20)

    if box1.is_longer_than(box2):
        container.fill(box1)
    else:
        container.fill(box2)


if __name__ == '__main__':
    main()