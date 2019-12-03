const desktop = {
  state: {
    iconSize: 64,
    posX: 10,
    posY: 10,
    defaultX: 10,
    defaultY: 10,
    margin: 10,
    maxY: 600,
  },
  icons: {
    docx: 'https://cdn3.iconfinder.com/data/icons/file-formats-15/625/File_Format_DOCX_Word_Microsoft_Office-512.png',
    empty: 'https://icon-library.net/images/recyle-bin-icon/recyle-bin-icon-8.jpg',
    full: 'https://vignette.wikia.nocookie.net/winodws/images/4/42/Full_Recycle_Bin.png/revision/latest?cb=20150728133507',
  },
  trash: {
    files: [],
    element: null,
  },

  files: [
    {
      id: 1,
      element: null,
    },
    {
      id: 2,
      element: null,
    },
    {
      id: 3,
      element: null,
    },
    {
      id: 4,
      element: null,
    },
  ],
  init(obj, top, left, icon, alt, size) {
    const img = document.createElement('img');
    img.draggable = false;
    img.alt = alt;
    img.src = icon;
    img.style.position = 'absolute';
    img.style.width = `${size}px`;
    img.style.top = `${top}px`;
    img.style.left = `${left}px`;
    img.style.zIndex = "1";
    img.addEventListener('mousedown', this.onMouseDown);
    obj.element = img;
    this.draw(img);
  },
  draw: function (elem) {
    document.body.appendChild(elem);
  },
  onMouseDown() {
    function collision(el1, el2) {
      el1.offsetBottom = el1.offsetTop + el1.offsetHeight;
      el1.offsetRight = el1.offsetLeft + el1.offsetWidth;
      el2.offsetBottom = el2.offsetTop + el2.offsetHeight;
      el2.offsetRight = el2.offsetLeft + el2.offsetWidth;

      return !((el1.offsetBottom < el2.offsetTop) ||
          (el1.offsetTop > el2.offsetBottom) ||
          (el1.offsetRight < el2.offsetLeft) ||
          (el1.offsetLeft > el2.offsetRight))
    }
    function onMouseUp(e){
      e.target.style.zIndex = "1";
      document.removeEventListener('mousemove', onMouseMove);
      e.target.removeEventListener('mouseup', onMouseUp);
      if (e.target !== desktop.trash.element && collision(e.target, desktop.trash.element)) {
        desktop.trash.files.push(e.target);
        e.target.remove();
      }
    }
    function onMouseMove(e) {
      e.target.style.zIndex = "1000";
      moveAt(e.target, e.pageX, e.pageY);
    }
    function moveAt(obj, x, y) {
      obj.style.left = `${x - obj.offsetWidth / 2}px`;
      obj.style.top = `${y - obj.offsetHeight / 2}px`;
    }

    this.addEventListener('mouseup', onMouseUp);
    document.addEventListener('mousemove', onMouseMove);
  }
};

desktop.init(desktop.trash, 500, 700, desktop.icons.empty, 'Recycle Bin', desktop.state.iconSize);
desktop.files.forEach(file => {
  desktop.init(file, desktop.state.posY, desktop.state.posX, desktop.icons.docx, 'file', desktop.state.iconSize);

  desktop.state.posY += desktop.state.iconSize + desktop.state.margin;
  if (desktop.state.posY >= desktop.state.maxY) {
    desktop.state.posY = desktop.state.defaultY;
    desktop.state.posX += desktop.state.iconSize + desktop.state.margin;
  }
});

