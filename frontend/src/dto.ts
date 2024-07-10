export type ListDto = {
  list_name: string,
  author: string,
  listitems: CheckListItemDto[],

}

export type CheckListItemDto = {
  text: string,
  checked: boolean,
}
