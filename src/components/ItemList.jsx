import Item from "./Item"

const ItemList = ({ itemList, onDelete, onEdit }) => {    
    return (
        <div className="itemList">
            <h2> 일기 리스트 </h2>
            {itemList.length}개의 일기가 있습니다.
            <div>
                {itemList.map((e) => (                    
                    <Item 
                    onDelete={onDelete}  
                    onEdit={onEdit}  
                    key={e.id} 
                    {...e}/>
                ))}
            </div>
        </div>
    )
}


export default ItemList;