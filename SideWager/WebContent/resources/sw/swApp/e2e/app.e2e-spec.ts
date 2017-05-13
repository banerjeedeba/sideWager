import { SwAppPage } from './app.po';

describe('sw-app App', () => {
  let page: SwAppPage;

  beforeEach(() => {
    page = new SwAppPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
